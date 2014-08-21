package org.dasein.cloud.aws.resource.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AttachmentData {

    @JsonProperty("data")
    private String data;

    @JsonProperty("fileName")
    private String fileName;

    @JsonProperty(value = "__type")
    private String type;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
