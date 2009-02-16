package photobox;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
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
        return getSimpleJdbcTemplate().query("select id, description, timestamp from image", new ParameterizedRowMapper<ImageDescriptor>() {

            @Override
            public ImageDescriptor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                int id = resultSet.getInt(1);
                String description = resultSet.getString(2);
                Date timestamp = new Date(resultSet.getDate(3).getTime());
                return new ImageDescriptor(id, description, timestamp);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public void streamImage(final int id, final OutputStream outputStream) {
        getJdbcTemplate().query(
                "select content_desktop from image where id = ?",
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
    @Transactional(readOnly = true)
    public void streamImageThumbnail(int id, OutputStream outputStream) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public void storeImage(final InputStream inputStream, final int contentLength, final String description) throws IOException {
        final byte[] data = ImageResizer.resizeToDesktop(inputStream);
        getJdbcTemplate().execute(
                "insert into image (description, timestamp, content_desktop) values (?, ?, ?)",
                new AbstractLobCreatingPreparedStatementCallback(this.lobHandler) {
                    @Override
                    protected void setValues(PreparedStatement preparedStatement, LobCreator lobCreator) throws SQLException, DataAccessException {
                        preparedStatement.setString(1, description);
                        preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
//                        lobCreator.setBlobAsBinaryStream(preparedStatement, 3, inputStream, contentLength);
                        lobCreator.setBlobAsBytes(preparedStatement, 3, data);
                    }
                });
    }

    @Override
    @Transactional
    public void clearDatabase() {
        getJdbcTemplate().update("delete from image");
    }
}
