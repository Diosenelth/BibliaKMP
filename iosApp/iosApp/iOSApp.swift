import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init(){
        HelperKtKt.doInitKoin()
    }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}