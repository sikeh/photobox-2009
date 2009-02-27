package photoboxme.util;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 * Created by IntelliJ IDEA. User: Sike Huang Date: Feb 22, 2009 Time: 9:21:46
 * PM To change this template use File | Settings | File Templates.
 */
public class Config {
	private final static String WEB_URL = "http://sikeh.s155.eatj.com/photobox/";
	private final static String SUFFIX_UPLOADER = "imageUpload.do";
	private final static String SUFFIX_IMAGE = "image.do?id=";

	private static Config instance;

	private Config() {
		//
	}

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

	public String getUploaderUrl() {
		return getWebUrl() + SUFFIX_UPLOADER;
	}

	public String getImageUrl(int id) {
		return getWebUrl() + SUFFIX_IMAGE + id;
	}

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
