package com.javabeast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jeffreya on 07/11/2016.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackerPreParsedMessage implements Serializable{

    private static final long serialVersionUID = -4557304960075040713L;

    @Id
    private String id;

    private String uuid;
    private String message;
    private Date timestamp;
}
