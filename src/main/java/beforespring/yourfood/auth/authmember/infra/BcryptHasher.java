package beforespring.yourfood.auth.authmember.infra;

import beforespring.yourfood.auth.authmember.domain.PasswordHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BcryptHasher implements PasswordHasher {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Bcrypt는 password를 자체 해쉬함수를 통해 Encoding 해주는 라이브러리 입니다.
     * SHA 알고리즘은 속도가 너무 빨라 brute force 로 뚫릴 가능성이 있는데
     * bcrypt 는 key setup 이라는 전처리로 해싱 시간을 원하는대로 늘릴수 있어
     * 이러한 위험성을 없앨 수 있다고 합니다.
     *
     * @param rawPassword 변형되기 전의 암호
     *
     * salt : 같은 password 라도 다른 해싱 결과를 만들기위해 평문에 추가하는 문자열
     * @return 헤쉬된 암호 -> 헤싱스트링를 리턴함
     */
    @Override
    public String hash(String rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    /**
     * checkpw의 경우 raw한 값과 헤싱스트링을 받아서
     * 동일한지 체크해줍니다.
     *
     */
    @Override
    public boolean isMatch(String raw, String hashed) {
        return bCryptPasswordEncoder.matches(raw, hashed);
    }
}
