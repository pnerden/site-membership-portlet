<%@ include file="/init.jsp" %>

<c:if test="${userId > 0}">
	<%
		PortletURL joinOrLeaveURL = renderResponse.createActionURL();
		joinOrLeaveURL.setWindowState(WindowState.MAXIMIZED);
		joinOrLeaveURL.setParameter(ActionRequest.ACTION_NAME, "joinOrLeave");
		joinOrLeaveURL.setParameter("redirect", currentURL);
		
		pageContext.setAttribute("joinOrLeaveURL", joinOrLeaveURL);
		
		int siteMembershipStatus = SiteMembershipServiceUtil.getSiteMembershipStatus(groupId, userId, request);
		pageContext.setAttribute("siteMembershipStatus", siteMembershipStatus);
		
		int requestimpossible = SiteMembershipKeys.REQUEST_IMPOSSIBLE;
		pageContext.setAttribute("requestimpossible", requestimpossible);
		int notamember = SiteMembershipKeys.NOT_A_MEMBER;
		pageContext.setAttribute("notamember", notamember);
		int statuspending = MembershipRequestConstants.STATUS_PENDING;
		pageContext.setAttribute("statuspending", statuspending);
		int statusapproved = MembershipRequestConstants.STATUS_APPROVED;
		pageContext.setAttribute("statusapproved", statusapproved);
	%>
</c:if>