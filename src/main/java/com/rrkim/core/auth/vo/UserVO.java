package com.rrkim.core.auth.vo;

import com.rrkim.core.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO {

    public UserVO(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.userNm = user.getUserNm();
        this.useYn = user.isUseYn();
    }

    private Long id;

    private String userId;

    private String userNm;

    private boolean useYn;

}
