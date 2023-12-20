# Parallel Sorting

Important Takeaways:
Parallelizing merge sort and quick sort does not always guarantee a faster runtime.
The way to truly reap the benefits of threading in this scenario is with arrays that are
very large in size (at least larger than 5 million elements). Otherwise, you are wasting more
time setting up the threads themselves instead of running the algorithms the standard way.
