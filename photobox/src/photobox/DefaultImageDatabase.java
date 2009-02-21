package photobox;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.FileCopyUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Sike Huang
 * Date: Feb 15, 2009
 * Time: 4:45:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultImageDatabase extends SimpleJdbcDaoSupport implements ImageDatabase {
    private LobHandler lobHandler;

    public void setLobHandler(LobHandler lobHandler) {
        this.lobHandler = lobHandler;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImageDescriptor> getImages() {
        return getSimpleJdbcTemplate().query("select id, description, timestamp from image order by timestamp desc", new ParameterizedRowMapper<ImageDescriptor>() {

            @Override
            public ImageDescriptor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);
                Date timestamp = new Date(resultSet.getTimestamp(3).getTime());
                return new ImageDescriptor(id, description, timestamp);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ImageDescriptor getImage(int id) {
        return getSimpleJdbcTemplate().queryForObject("select id, description, timestamp from image where id = ?", new ParameterizedRowMapper<ImageDescriptor>() {

            @Override
            public ImageDescriptor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);
                Date timestamp = new Date(resultSet.getTimestamp(3).getTime());
                return new ImageDescriptor(id, description, timestamp);
            }
        }, new Object[]{id});
    }

    @Override
    @Transactional(readOnly = true)
    public void streamImage(final int id, final OutputStream outputStream, DbColumns dbColumn) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        sb.append(dbColumn.getColumnName());
        sb.append(" from image where id = ?");
        getJdbcTemplate().query(
//                "select  from image where id = ?",
                sb.toString(),
                new Object[]{id},
                new AbstractLobStreamingResultSetExtractor() {
                    @Override
                    protected void handleNoRowFound() throws LobRetrievalFailureException {
                        throw new EmptyResultDataAccessException(
                                "Image with id '" + id + "' not found in database", 1);
                    }

                    @Override
                    public void streamData(ResultSet rs) throws SQLException, IOException {
                        InputStream is = lobHandler.getBlobAsBinaryStream(rs, 1);
                        if (is != null) {
                            FileCopyUtils.copy(is, outputStream);
                        }
                    }
                }
        );
    }

    @Override
    @Transactional
    public void storeImage(final InputStream inputStream, final int contentLength, final String description) throws IOException {
        final byte[] originalData = FileCopyUtils.copyToByteArray(inputStream);
        final byte[] mobileData = ImageResizer.resizeToMobile(originalData);
        final byte[] mobileThumbnailData = ImageResizer.resizeToMobileThumbnail(originalData);
        final byte[] desktopData = ImageResizer.resizeToDesktop(originalData);
        final byte[] desktopThumbnailData = ImageResizer.resizeToDesktopThumbnail(originalData);
        getJdbcTemplate().execute(
                "insert into image (description, timestamp, content_mobile, content_mobile_thumbnail, content_desktop, content_desktop_thumbnail) values (?, ?, ?, ?, ?, ?)",
                new AbstractLobCreatingPreparedStatementCallback(this.lobHandler) {
                    @Override
                    protected void setValues(PreparedStatement preparedStatement, LobCreator lobCreator) throws SQLException, DataAccessException {
                        preparedStatement.setString(1, description);
                        preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
//                        lobCreator.setBlobAsBinaryStream(preparedStatement, 3, inputStream, contentLength);
                        lobCreator.setBlobAsBytes(preparedStatement, 3, mobileData);
                        lobCreator.setBlobAsBytes(preparedStatement, 4, mobileThumbnailData);
                        lobCreator.setBlobAsBytes(preparedStatement, 5, desktopData);
                        lobCreator.setBlobAsBytes(preparedStatement, 6, desktopThumbnailData);
                    }
                });
    }

    @Override
    @Transactional
    public void clearDatabase() {
        getJdbcTemplate().update("delete from image");
    }
}
