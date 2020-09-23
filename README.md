# android-master-detail-42
Displays a list of items obtained from iTunes Search API and shows a detailed view of each item

Using Android Studio 4.0.1, Target SDK = 29 (Min = 21)

---

*Persistence:</br>
Mechanism: Shared Preferences</br>
Persistent data:
> lastVisit = date/time when the user previously visited</br> 
> isGridLayout = last master view layout used; user can toggle layout on Master View (default is Grid, alternative is Linear)</br>

*Design Pattern:</br>
> AppPreferences = Singleton pattern for handling persistent data
