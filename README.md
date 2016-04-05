
Round 1
* I started by wrapping some unit tests on the code as it was.
* Then I refactored into a shared method with a parameter for retrieving content.
* I would continue by refactoring into try with resources to ensure closes, and likely using IOUtils instead.

=====

Round 2
* Added a test of the saveContent method where I had not had one earlier.
* Removed member variable of type File to ensure thread safety across methods.
* Used try with resources to ensure closed streams.
* Bumped hex check from 80 to FF and added tests for some affected chars.
