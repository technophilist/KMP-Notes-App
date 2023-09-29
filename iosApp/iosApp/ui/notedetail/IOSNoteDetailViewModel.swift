import Foundation
import shared

extension NoteDetailScreen {
    class IOSNoteDetailViewModel : ObservableObject {
        
        private var noteDetailViewModel: NoteDetailViewModel
        
        init(notesRepository:NotesRepository,currentNoteId:String?) {
            self.noteDetailViewModel = NoteDetailViewModel(
                currentNoteId: currentNoteId,
                notesRepository: notesRepository,
                coroutineScope: nil
            )
        }
        
        func onTitleChange(_ newTitle:String){
            noteDetailViewModel.onTitleChange(newTitle: newTitle)
        }
        
        func onContentChange(_ newContent:String){
            noteDetailViewModel.onContentChange(newContent: newContent)
        }
    
    }
}

