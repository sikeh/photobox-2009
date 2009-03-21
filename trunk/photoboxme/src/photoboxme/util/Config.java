package photoboxme.util;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * Hold constants, PhotoBox Web url, uploader url, url of an image. Also provide
 * methods for setting and getting those url.
 * 
 * @author Sike Huang
 * 
 */
public class Config {
	private final static String WEB_URL = "http://sikeh.s155.eatj.com/photobox/";
	private final static String SUFFIX_UPLOADER = "imageUpload.do";
	private final static String SUFFIX_IMAGE = "image.do?id=";

	private static Config instance;

	private Config() {
		//
	}

	/**
	 * Init the singleton.
	 * 
	 * @return
	 */
	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
			try {
				RecordStore recordStore = RecordStore.openRecordStore(
						"photoboxme", true);
				String url = System.getProperty("web-url");
				if (recordStore.getNumRecords() == 0) {
					if (url == null) {
						url = WEB_URL;
					}
					recordStore.addRecord(url.getBytes(), 0,
							url.getBytes().length);
				}
			} catch (RecordStoreException e) {
				e.printStackTrace(); // To change body of catch statement use
				// File | Settings | File Templates.
			}
		}
		return instance;
	}

	/**
	 * Return PhotoBox Web url.
	 * 
	 * @return web url
	 */
	public String getWebUrl() {
		String url = null;
		try {
			RecordStore rs = RecordStore.openRecordStore("photoboxme", false);
			url = new String(rs.getRecord(1));
		} catch (RecordStoreException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}
		return url;
	}

	/**
	 * Returtn the uploader url, used to do HTTP Multipart POST.
	 * 
	 * @return uploader url
	 */
	public String getUploaderUrl() {
		return getWebUrl() + SUFFIX_UPLOADER;
	}

	/**
	 * Return the url of an image with given id.
	 * 
	 * @param id
	 *            identifier of the image
	 * @return url of the image
	 */
	public String getImageUrl(int id) {
		return getWebUrl() + SUFFIX_IMAGE + id;
	}

	/**
	 * Update the PhotoBox Web url, stored in Record Store.
	 * 
	 * @param webUrl
	 *            web url to be set
	 */
	public void setWebUrl(String webUrl) {
		try {
			RecordStore rs = RecordStore.openRecordStore("photoboxme", false);
			rs.setRecord(1, webUrl.getBytes(), 0, webUrl.getBytes().length);
		} catch (RecordStoreException e) {
			e.printStackTrace(); // To change body of catch statement use File |
			// Settings | File Templates.
		}
	}
}
