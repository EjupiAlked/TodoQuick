package com.ejupialked.todoapp.domain.usecase;

import com.ejupialked.todoapp.data.repository.Repository;
import com.ejupialked.todoapp.domain.model.TypeTask;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class RemoveTaskType extends UseCase<TypeTask> {

    private final Repository repository;
    private TypeTask t;


    @Inject
    RemoveTaskType(@Named("executor_thread") Scheduler executorThread, @Named("ui_thread") Scheduler uiThread, Repository repository) {
        super(executorThread, uiThread);
        this.repository = repository;
    }


    public void removeTaskType(TypeTask t){
        this.t = t;
    }


    @Override
    protected Observable<TypeTask> createObservableUseCase() {
        return this.repository.removeTaskType(t);
    }
}
