package com.atracker.data.tracking;

public class CartBaseInfo extends BaseInfo{

	 
	public CartBaseInfo(String userUid) {
		super(userUid);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getUserUId();
	}

}
