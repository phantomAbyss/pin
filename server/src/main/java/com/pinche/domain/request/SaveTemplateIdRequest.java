package com.pinche.domain.request;


/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class SaveTemplateIdRequest extends BaseRequest {
    private String templateId;

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
