package com.cygrove.libcore.updateversion.task;


import com.cygrove.libcore.updateversion.request.GetRequest;
import com.cygrove.libcore.updateversion.request.IRequest;

import java.util.Map;


public class GetTask extends BaseTask<GetTask> {

    public GetTask(String url) {
        super(url);
    }

    public GetTask(String url, Map<String, String> params) {
        super(url, params);
    }

    @Override
    public void execute() {
        IRequest request = new GetRequest(mUrl, mParams, mCallback);
        request.request();
    }
}
