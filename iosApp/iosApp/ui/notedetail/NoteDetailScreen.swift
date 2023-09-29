import Foundation
import shared
import SwiftUI

struct NoteDetailScreen : View {
    @ObservedObject private var noteDetailViewModel:IOSNoteDetailViewModel
    
    @State var titleText = ""
    @State var contentText = ""
    
    init(appModule:AppModule, note:Note? = nil) {
        self.noteDetailViewModel = IOSNoteDetailViewModel(
            notesRepository: appModule.provideNotesRepository(),
            currentNoteId: note?.id
        )
        titleText = note?.title ?? ""
        contentText = note?.content ?? ""
    }
    
    var body : some View {
        VStack(alignment: .center) {
            TextField("Title",text: $titleText,axis: .vertical)
                .onChange(of: titleText){
                    print($0)
                    noteDetailViewModel.onTitleChange($0)
                }
                .font(.largeTitle.weight(.bold))
            
            TextEditor(text: $contentText)
                .onChange(of: contentText){ noteDetailViewModel.onContentChange($0) }
                .font(.headline)
            
        }
        .padding(.horizontal)
        .navigationBarTitleDisplayMode(.inline)
    }
}
