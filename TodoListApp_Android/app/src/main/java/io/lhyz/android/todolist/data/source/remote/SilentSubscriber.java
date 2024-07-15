package io.lhyz.android.todolist.data.source.remote;

import io.reactivex.rxjava3.annotations.NonNull;

public class SilentSubscriber<T> extends DefaultSubscriber<T> {
    @Override
    public void onSuccess(T result) {

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
    }
}
