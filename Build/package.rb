def prepare_file (project_name)
  dest = File.open("target/" + project_name + ".scala", "w")

  dest.write("// project_name created on " + Time.new().inspect()  + "\n\n")

  Dir.glob('../' + project_name + '/src/*.scala') do |item|
    src = File.open(item)
    IO.copy_stream(src, dest)
    dest.write("\n\n// -----------------------------------------------------------------------------\n\n")
  end

  dest.write("\n\n// " + project_name + " finished on " + Time.new().inspect()  + "\n\n")
  dest.flush()
end

def display_in_console(project_name)
  print IO.read('target/' + project_name + '.scala')
end

prepare_file('PlatinumRift')
prepare_file('PokerChipRace')
prepare_file('Tron')
prepare_file('BackToTheCode')
prepare_file('MarsLanderLvl2')

display_in_console('MarsLanderLvl2')
