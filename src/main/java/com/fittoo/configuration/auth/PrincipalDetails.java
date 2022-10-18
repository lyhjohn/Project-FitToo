//package com.fittoo.configuration.auth;
//
//import com.fittoo.common.message.ErrorMessage;
//import com.fittoo.member.entity.Member;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
///**
// * 시큐리티가 /login 주소 요청을 낚아채서 로그인을 진행시킨다.
// * 로그인 진행 완료가 되면 Security Session을 만든다. (Security ContextHolder)
// * 세션에 들어가는 오브젝트: Authentication 타입의 객체
// * Authentication 안에 UserDetails 타입의 User 정보가 있어야 됨
// */
//public class PrincipalDetails implements UserDetails {
//
//    private Member member;
//
//    public PrincipalDetails(Member member) {
//        this.member = member;
//    }
//
//    // 해당 유저의 권한을 리턴하는 곳
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return member.getLoginType().toString();
//            }
//        });
//        return collection;
//    }
//
//    @Override
//    public String getPassword() {
//        return member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return member.getUserId();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
