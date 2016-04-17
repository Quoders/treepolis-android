package com.quoders.apps.android.treepolis.ui.wikiSelection;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.quoders.apps.android.treepolis.R;
import com.quoders.apps.android.treepolis.model.checkin.WikiTreeLink;
import com.quoders.apps.android.treepolis.utils.FileUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by david on 17/04/16.
 */
public class WikiTreeInteractorImpl implements WikiTreeInteractor {

    private final static String TAG = WikiTreeInteractorImpl.class.getSimpleName();

    private final Context mContext;

    public WikiTreeInteractorImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public Observable<List<WikiTreeLink>> loadWikiTreeLinks() {

        return Observable.defer(new Func0<Observable<List<WikiTreeLink>>>() {
            @Override
            public Observable<List<WikiTreeLink>> call() {
                return Observable.just(FileUtils.readJsonFile(mContext, mContext.getString(R.string.wiki_trees_json)))
                        .map(new Func1<String, List<WikiTreeLink>>() {
                            @Override
                            public List<WikiTreeLink> call(String json) {
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<WikiTreeLink>>() {}.getType();
                                List<WikiTreeLink> fromJson = gson.fromJson(json, type);
                                return fromJson;
                            }
                        });
            }
        }).subscribeOn(Schedulers.newThread());
    }
}