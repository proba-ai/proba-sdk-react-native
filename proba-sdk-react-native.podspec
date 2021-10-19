require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "proba-sdk-react-native"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "9.0" }
  s.source       = { :git => "https://github.com/proba-ai/proba-sdk-react-native.git", :tag => "#{s.version}" }

  
  s.source_files = "ios/**/*.{h,m,mm,swift}"
  
  s.swift_version = "5.0"

  s.dependency "React"
  s.dependency "Proba", "0.2.7"
end
