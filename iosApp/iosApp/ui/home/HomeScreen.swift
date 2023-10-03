import Foundation
import SwiftUI
import shared

struct HomeScreen : View {
    @ObservedObject private var homeViewModel:IOSHomeViewModel
    @State private var searchText = ""
   
    private let appModule:AppModule

    
    init(appModule: AppModule) {
        self.appModule = appModule
        self.homeViewModel = IOSHomeViewModel(
            notesRepository: appModule.provideNotesRepository(),
            dispatchersProvider: appModule.provideDispatchersProvider()
        )
    }
    var body: some View {
        List {
            if !searchText.isEmpty {
                ForEach(homeViewModel.uiState.searchResults, id:\.self.id) { note in
                    NavigationLink(
                        destination: NoteDetailScreen(appModule: appModule,note:note),
                        label: { NoteListCard(note: note) }
                    )
                }.onDelete { indexSet in homeViewModel.deleteNotes(indexSet: indexSet) }
            } else {
                ForEach(homeViewModel.uiState.savedNotes, id:\.self.id) { note in
                    NavigationLink(
                        destination: NoteDetailScreen(appModule: appModule,note:note),
                        label: { NoteListCard(note: note) }
                    )
                }.onDelete { indexSet in homeViewModel.deleteNotes(indexSet: indexSet) }
            }
        }
        .searchable(text: $searchText)
        .onChange(of: searchText) { homeViewModel.search(searchText: $0) }
        .toolbar {
            NavigationLink(
                destination: NoteDetailScreen(appModule: appModule),
                label: { Image(systemName: "plus") }
            )
        }
        .navigationTitle("Notes")
        .onAppear { homeViewModel.subscribeForUiStateUpdates() }
        .onDisappear{ homeViewModel.unsubscribeForUiStateUpdates() }
    }
}
