import Foundation
import shared
import SwiftUI

struct NoteDetailScreen : View {
    @State var titleText = ""
    @State var contentText = ""
    var body : some View {
        VStack(alignment: .center) {
            TextField("Title",text:$contentText,axis: .vertical)
                .font(.largeTitle.weight(.bold))

            TextEditor(text: $titleText)
                .font(.headline)
            
        }.frame(maxWidth: .infinity,maxHeight: .infinity,alignment: .topLeading)
            .padding(.horizontal)
    }
}
