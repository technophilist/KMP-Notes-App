import SwiftUI
import shared

private let appModule = AppModule()

struct ContentView: View {
    
    var body: some View {
        NavigationView {
            HomeScreen(appModule: appModule)
        }.accentColor(.yellow)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
