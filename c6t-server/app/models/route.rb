class Route < ActiveRecord::Base
  belongs_to :user
  has_many :quests

  def save_quest_image(quest_number, image_file)
    dest_dir_path = Rails.root.to_s + '/public/routes/' + self.id.to_s + '/images/'
    dest_file_path = dest_dir_path + quest_number.to_s + '.jpg'

    FileUtils.mkdir_p dest_dir_path
    File.open(dest_file_path, 'wb') do |dest|
      dest.write(image_file.read)
    end

    dest_file_path
  end
end
