# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20130821153928) do

  create_table "explorations", force: true do |t|
    t.datetime "start_time",                                             null: false
    t.integer  "route_id",                                               null: false
    t.integer  "user_id",                                                null: false
    t.integer  "current_quest_number"
    t.integer  "current_mission_completed_number_count", default: 0
    t.boolean  "photographed",                           default: false
    t.text     "description",                                            null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "explorations", ["route_id"], name: "index_explorations_on_route_id"
  add_index "explorations", ["user_id"], name: "index_explorations_on_user_id"

  create_table "quests", force: true do |t|
    t.string   "location",   null: false
    t.text     "pose",       null: false
    t.text     "mission",    null: false
    t.string   "photo"
    t.integer  "route_id",   null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "quests", ["route_id"], name: "index_quests_on_route_id"

  create_table "routes", force: true do |t|
    t.string   "name",                          null: false
    t.integer  "achievement_count", default: 0
    t.integer  "played_count",      default: 0
    t.string   "start_location",                null: false
    t.text     "description",                   null: false
    t.integer  "user_id",                       null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "routes", ["name"], name: "index_routes_on_name", unique: true
  add_index "routes", ["user_id"], name: "index_routes_on_user_id"

  create_table "users", force: true do |t|
    t.string   "name",       null: false
    t.string   "area",       null: false
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "users", ["name"], name: "index_users_on_name", unique: true

  create_table "users_explorations", force: true do |t|
    t.integer "user_id"
    t.integer "exploration_id"
  end

end
