package org.qamation.webdriver.utils;

public interface BiAction<T,S> {
    T perform(T arg1, S arg2);
}
