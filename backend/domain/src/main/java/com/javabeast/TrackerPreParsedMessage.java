package com.javabeast;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by jeffreya on 07/11/2016.
 *
 */

@Data
@Builder
public class TrackerPreParsedMessage {

    private Date timeStamp;
    private byte [] data;

}
