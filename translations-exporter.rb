#!/usr/bin/env ruby

if (ARGV.count < 1) then
  puts "Usage: translations-exporter.rb translationsfile"
else
  filename = ARGV[0]
  file = File.open(filename, 'rb:UTF-16LE')
  lines = file.readlines
  file.close
  pattern = '\"(.+)\" = \"(.+)\";\n'
  regex = Regexp.new(pattern.encode(lines[0].encoding),16)
  translations = lines.map{| line | regex.match(line) }.reject {|x| x == nil}.map {|x| [x[1], x[2]]}
  pattern = '/\* (.+) \*/\n'
  regex = Regexp.new(pattern.encode(lines[0].encoding),16)
  comments = lines.map{| line | regex.match(line) }.reject {|x| x == nil}.map{|x| x[1]}
  data = translations.zip(comments).map{|x| {:key => x[0][0], :value => x[0][1], :comment=> x[1]}}
  comma = ','.encode('UTF-16LE')
  filename = filename.encode('UTF-16LE')
  data.each do |x|
    puts "#{filename}#{comma}#{x[:key]}#{comma}#{x[:value]}#{comma}#{x[:comment]}"
  end
end



