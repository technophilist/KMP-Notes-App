import Foundation
import SwiftUI
import shared

struct HomeScreen : View {
    @ObservedObject private var homeViewModel:IOSHomeViewModel
    
    init(appModule: AppModule) {
        self.homeViewModel = IOSHomeViewModel(
            notesRepository: appModule.provideNotesRepository(),
            dispatchersProvider: appModule.provideDispatchersProvider()
        )
    }
    var body: some View {
        List {
            ForEach(homeViewModel.uiState.savedNotes, id:\.self.id) { note in
                NoteListCard(note: note)
            }.onDelete {  indexSet in homeViewModel.deleteNotes(indexSet: indexSet) }
        }
        .searchable(text: $homeViewModel.searchText)
        .onDisappear{ homeViewModel.dispose() }
    }
}
