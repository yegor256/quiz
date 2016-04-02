
I started by wrapping some unit tests on the code as it was.

Then I refactored into a shared method with a parameter for retrieving content.

I would continue by refactoring into try with resources to ensure closes, and likely using IOUtils instead.
