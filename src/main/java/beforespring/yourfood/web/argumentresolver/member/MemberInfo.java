package beforespring.yourfood.web.argumentresolver.member;

public record MemberInfo(
    Long memberId,
    Long authMemberId,
    String username
) {

}
