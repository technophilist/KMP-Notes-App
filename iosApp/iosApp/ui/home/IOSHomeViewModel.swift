import Foundation
import shared


extension HomeScreen{
    
    class IOSHomeViewModel : ObservableObject {
        
        private var homeViewModel : HomeViewModel
        private var collectHandle : Kotlinx_coroutines_coreDisposableHandle? = nil
        
        @Published var searchText = ""
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
            collectHandle = homeViewModel.uiState.collect { newUiState in
                if(newUiState != nil){
                    self.uiState = newUiState!
                }
            }
        }
        
        func search(searchText: String) {
            homeViewModel.search(searchText: searchText)
        }
        
        func deleteNotes(indexSet:IndexSet) {
            indexSet.forEach { homeViewModel.deleteNote(note: uiState.savedNotes[$0]) }
        }
        
        func dispose(){
            collectHandle?.dispose()
        }
    }
    
}

