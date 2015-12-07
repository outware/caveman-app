package au.com.outware.cavemanapp.domain.interactor;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public interface UseCase <T> {
    T execute();
}
