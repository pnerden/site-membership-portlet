<%@ include file="/join/init.jsp" %>

<c:if test="${userId > 0}">

<div class="site-membership-portlet" id="join-or-leave-button">
	<span>
	<c:if test="${siteMembershipStatus == notamember}">
		<input id="join-button" class="aui-button-input" onclick="location.href = '${joinOrLeaveURL}'" type="button" value="<%=LanguageUtil.get(pageContext, "join-site")%>" />
	</c:if>
	<c:if test="${siteMembershipStatus == statuspending}">
		<input id="pending-button" class="aui-buttonitem-disabled" type="button" disabled="disabled" value="<%= LanguageUtil.get(pageContext, "pending-approval") %>" />
	</c:if>
	<c:if test="${siteMembershipStatus == statusapproved}">
		<input id="leave-button" class="aui-button-input" onclick="location.href = '${joinOrLeaveURL}'" type="button" value="<%=LanguageUtil.get(pageContext, "leave-site")%>" />	
	</c:if>
	</span>
</div>

<script type="text/javascript" charset="utf-8">
	AUI().use(function(A) {
		var joinorlivebuttondiv = A.one("#join-or-leave-button");
		if (A.one("#join-or-leave-button span input") != null) {
			if (A.one("#join-or-leave-button span input").get('id') == "leave-button") {
				A.one("#navigation").insert(joinorlivebuttondiv, "after");
			} else {
				A.one("#navigation").insert(joinorlivebuttondiv, "before");
				joinorlivebuttondiv.setStyle("margin-top", 0-document.getElementById("navigation").offsetHeight);
			}
		}
	});
</script>

</c:if>