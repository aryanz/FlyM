package co.in.aryanz.FlyM.util;

public class FriendsPOJO {
	
	private String fid[];
	private String uid;
	
	public FriendsPOJO(String uid, String[] fid) {
		this.uid = uid;
		this.fid = fid;
	}
	
	
	/**
	 * @return the fid
	 */
	public String[] getFid() {
		return fid;
	}
	/**
	 * @param fid the fid to set
	 */
	public void setFid(String[] fid) {
		this.fid = fid;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	

}
