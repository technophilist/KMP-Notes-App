import Foundation
import shared

extension NoteDetailScreen {
    class IOSNoteDetailViewModel : ObservableObject {
        
        private var noteDetailViewModel: NoteDetailViewModel
        private var titleTextStreamHandle: Kotlinx_coroutines_coreDisposableHandle? = nil
        private var contentTextStreamHandle: Kotlinx_coroutines_coreDisposableHandle? = nil
        
        @Published var titleText = ""
        @Published var contentText = ""
        
        init(notesRepository:NotesRepository,currentNoteId:String?) {
            self.noteDetailViewModel = NoteDetailViewModel(
                currentNoteId: currentNoteId,
                notesRepository: notesRepository,
                coroutineScope: nil
            )
            titleTextStreamHandle = noteDetailViewModel.titleTextStream.collect { updatedTitle in
                self.titleText = updatedTitle! as String
            }
            noteDetailViewModel.contentTextStream.collect { updatedContent in
                self.contentText = updatedContent! as String
            }
        }
        
        func onTitleChange(_ newTitle:String){
            noteDetailViewModel.onTitleChange(newTitle: newTitle)
        }
        
        func onContentChange(_ newContent:String){
            noteDetailViewModel.onContentChange(newContent: newContent)
        }
        
        func dispose(){
            titleTextStreamHandle?.dispose()
            contentTextStreamHandle?.dispose()
        }
    }
}

