import Foundation
import shared
import SwiftUI

struct NoteDetailScreen : View {
    @ObservedObject private var noteDetailViewModel:IOSNoteDetailViewModel
    
    init(appModule:AppModule) {
        self.noteDetailViewModel = IOSNoteDetailViewModel(
            notesRepository: appModule.provideNotesRepository(),
            currentNoteId: nil
        )
    }
    
    var body : some View {
        VStack(alignment: .center) {
            TextField("Title",text: $noteDetailViewModel.titleText,axis: .vertical)
                .onChange(of: noteDetailViewModel.contentText){ noteDetailViewModel.onTitleChange($0) }
                .font(.largeTitle.weight(.bold))
            
            TextEditor(text: $noteDetailViewModel.contentText)
                .onChange(of: noteDetailViewModel.contentText){ noteDetailViewModel.onContentChange($0) }
                .font(.headline)
            
        }
        .padding(.horizontal)
        .background(Color.red)
    }
}
