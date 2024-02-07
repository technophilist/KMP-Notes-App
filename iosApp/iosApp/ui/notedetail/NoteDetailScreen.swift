import Foundation
import shared
import SwiftUI

struct NoteDetailScreen : View {
    @ObservedObject private var noteDetailViewModel:IOSNoteDetailViewModel
    
    @State private var titleText = ""
    @State private var contentText = "Content"
    
    init(appModule:AppModule, note:Note? = nil) {
        self.noteDetailViewModel = IOSNoteDetailViewModel(
            notesRepository: appModule.provideNotesRepository(),
            currentNoteId: note?.id
        )
        guard let note else { return }
        _titleText = State(initialValue: note.title)
        _contentText = State(initialValue: note.content)
    }
    
    var body : some View {
        VStack(alignment: .center) {
            TextField("Title",text: $titleText,axis: .vertical)
                .onChange(of: titleText){ noteDetailViewModel.onTitleChange($0) }
                .font(.largeTitle.weight(.bold))
            
            TextEditor(text: $contentText)
                .onChange(of: contentText){ noteDetailViewModel.onContentChange($0) }
                .font(.headline)
            
        }
        .padding(.horizontal)
        .navigationBarTitleDisplayMode(.inline)
    }
}
