package com.example.notesapp

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

//The function allows you to write more streamlined and readable tests for LiveData
// objects by simplifying the process of waiting for the first emitted value.
@VisibleForTesting(otherwise = VisibleForTesting.NONE) //none - should never be called from production code
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {

        override fun onChanged(value: T) {
            data = value
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}

/**
 *
Here's how the function works:

The getOrAwaitValue() function extends LiveData<T>, allowing you to call it directly on instances of LiveData.
The time and timeUnit parameters specify the maximum time to wait for the value to be emitted before throwing a TimeoutException.
The afterObserve parameter is a callback function that can be used to perform additional actions after observing the LiveData.
Inside the function, a CountDownLatch is created with an initial count of 1 to control the waiting process.
An observer is defined to capture the first emitted value, update the data variable, and release the latch.
The observer is added to the LiveData using observeForever().
The provided afterObserve callback is invoked after observing the LiveData.
The function waits for the value to be emitted using latch.await(). If the timeout occurs, a TimeoutException is thrown.
Finally, the observer is removed from the LiveData to clean up.

 * */