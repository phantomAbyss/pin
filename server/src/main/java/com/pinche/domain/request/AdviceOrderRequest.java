package com.pinche.domain.request;

import com.pinche.domain.address.Dot;

/**
 * @author Parmaze
 * @date 2021/12/16
 */
public class AdviceOrderRequest extends BaseRequest{
    private Dot dot;

    public Dot getDot() {
        return dot;
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdviceOrderRequest that = (AdviceOrderRequest) o;

        return dot != null ? dot.equals(that.dot) : that.dot == null;
    }

    @Override
    public int hashCode() {
        return dot != null ? dot.hashCode() : 0;
    }
}
