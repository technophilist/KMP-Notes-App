import Foundation
import SwiftUI
import shared

/// A view that displays the information of a Note object.
/// Some of the contents might get truncated if the content property of the Note object is too long.
struct NoteListCard : View{
    var note : Note
    var body : some View{
        VStack(alignment: .leading){
            Text(note.title)
                .font(.headline)
                .fontWeight(.bold)
                .lineLimit(2)
            
            if !note.content.isEmpty {
                Text(note.content)
                    .lineLimit(3)
            }
        }
    }
}
