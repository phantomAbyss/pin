package com.pinche.domain.request;

import com.pinche.domain.address.Dot;

import javax.validation.constraints.NotNull;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class FuzzyOrderRequest extends BaseRequest {
    @NotNull(message = "startAddress can't null")
    private Dot startDot;
    @NotNull(message = "endAddress can't null")
    private Dot endDot;

    public Dot getStartDot() {
        return startDot;
    }

    public void setStartDot(Dot startDot) {
        this.startDot = startDot;
    }

    public Dot getEndDot() {
        return endDot;
    }

    public void setEndDot(Dot endDot) {
        this.endDot = endDot;
    }
}
