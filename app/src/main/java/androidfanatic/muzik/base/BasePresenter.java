package androidfanatic.muzik.base;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    CompositeSubscription subscriptions;

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    @Override public void attachView(V view) {
        super.attachView(view);
        subscriptions = new CompositeSubscription();
    }

    @Override public void detachView(boolean retainInstance) {
        subscriptions.unsubscribe();
        super.detachView(retainInstance);
    }
}

