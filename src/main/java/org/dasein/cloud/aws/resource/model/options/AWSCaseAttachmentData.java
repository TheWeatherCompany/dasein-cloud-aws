package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketAttachmentData;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseAttachmentData {
//    @JsonProperty("data")
//    private String data;
//    @JsonProperty("fileName")
//    private String fileName;

    private TicketAttachmentData _data;

    private AWSCaseAttachmentData() {
    }

    private AWSCaseAttachmentData(TicketAttachmentData _data) {
        this._data = _data;
    }

    public static AWSCaseAttachmentData getInstance(TicketAttachmentData data) {
        return new AWSCaseAttachmentData(data);
    }

    @JsonProperty("data")
    public String getData() {
        return _data.getData();
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return _data.getFileName();
    }
}
