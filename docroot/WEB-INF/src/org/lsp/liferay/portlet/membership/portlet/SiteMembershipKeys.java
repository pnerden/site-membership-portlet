package org.lsp.liferay.portlet.membership.portlet;

import com.liferay.portal.model.MembershipRequestConstants;

public class SiteMembershipKeys {
	
	public static final int MEMBERSHIP_TYPE_PUBLIC = 1;
	public static final int MEMBERSHIP_TYPE_RESTRICTED = 2;
	public static final int MEMBERSHIP_TYPE_PRIVATE = 3;
	
	public static final int STATUS_APPROVED = MembershipRequestConstants.STATUS_APPROVED;
	public static final int STATUS_PENDING = MembershipRequestConstants.STATUS_PENDING;
	public static final int NOT_A_MEMBER = -42;
	public static final int REQUEST_IMPOSSIBLE = -142;
}
