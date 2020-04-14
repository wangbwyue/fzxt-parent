package com.fzxt.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CheckChunkResult extends Result {

    public CheckChunkResult(StatusCode resultCode, boolean fileExist) {
        this.fileExist = fileExist;
    }

    boolean fileExist;
}
