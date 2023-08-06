# MovieP

The app was developed in :
- Kotlin
- MVVM Architectural pattern
- Hilt
- Coroutines
- Mockito for unit testing

## Project Structure
Globally the project has the following top level packages:
1. **data**: Basically data layer that contains data source both local or remote, and all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Dagger-Hilt.
3. **ui**: Basically presenter layer that contains the classes View (Activity, Fragment), Adapter, ViewModel.
4. **domain**: Basically domain layer.
5. **utils**: Contains Utility & Helper classes.


## Technical Debts
- need bump up coverage test for UI test

## Credits
- **Dimas Arya Murdiyan** - dimasaryamurdiyan123@gmail.com