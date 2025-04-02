package com.rrkim.core.auth.dto.data;

import com.rrkim.core.auth.domain.Eula;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EulaDto {

    public EulaDto(Eula eula) {
        this.eulaId = eula.getEulaId();
        this.content = eula.getContent();
        this.requireYn = eula.isRequireYn();
    }

    private String eulaId;

    private String content;

    private boolean requireYn;
}
