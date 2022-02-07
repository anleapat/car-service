package car.app.dto;

import lombok.Data;

@Data
public class BaseResult {
    private static final String FAILED = "failed";

    private int resultCode;
    private Object result;
    private String msg;

    public static BaseResult ok(Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.setResultCode(200);
        baseResult.setResult(data);
        return baseResult;
    }

    public static BaseResult error(String msg) {
        BaseResult baseResult = new BaseResult();
        baseResult.setResultCode(201);
        baseResult.setMsg(msg);
        return baseResult;
    }

    private BaseResult() {

    }
}
