import Foundation
import shared


extension HomeScreen{
    
    class IOSHomeViewModel : ObservableObject {
        
        private var homeViewModel : HomeViewModel
        private var collectHandle : Kotlinx_coroutines_coreDisposableHandle? = nil
        
        @Published var uiState: HomeScreenUiState = HomeScreenUiState(
            isLoadingSavedNotes: false,
            isLoadingSearchResults: false,
            savedNotes: [],
            searchResults: []
        )
        
        init(notesRepository : NotesRepository , dispatchersProvider : DispatchersProvider){
            homeViewModel = HomeViewModel(
                notesRepository: notesRepository,
                coroutineScope: nil,
                defaultDispatcher: dispatchersProvider.defaultDispatcher
            )
        }
        
        func search(searchText: String) {
            homeViewModel.search(searchText: searchText)
        }
        
        func deleteNotes(indexSet:IndexSet) {
            indexSet.forEach { homeViewModel.deleteNote(note: uiState.savedNotes[$0]) }
        }
        
        func subscribeForUiStateUpdates(){
            collectHandle = homeViewModel.uiState.subscribe { newUiState in
                if(newUiState != nil){
                    self.uiState = newUiState!
                }
            }
        }
        
        func unsubscribeForUiStateUpdates(){
            collectHandle?.dispose()
        }
    }
    
}

