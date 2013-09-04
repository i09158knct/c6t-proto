class ExplorationsController < ApplicationController
  protect_from_forgery except: [:start, :put_group_photo, :put_mission_photo]

  before_action :set_exploration, only: [
    :show, :edit, :update, :destroy, :get_members, :add_member, :start,
    :get_current_quest_number, :put_group_photo, :put_mission_photo,
  ]

  # GET /explorations
  # GET /explorations.json
  def index
    if params[:query].present? && params[:for].present?
      query = params[:query]
      case params[:for]
      when 'route_id'
        @explorations = Exploration
          .where(route_id: query)

      when 'route_title'
        @explorations = Exploration
          .joins(:route)
          .where('routes.name like ?', "%#{query}%")

      when 'user_name'
        @explorations = Exploration
          .joins(:host)
          .where(users: {name: query})

      else
        raise "Invalid Param (for: #{params[:for]})"
      end

    else
      @explorations = Exploration.all
    end

    order = (params[:order] == 'asc') ? 'ASC' : 'DESC'
    @explorations.order!("start_time #{order}")
  end

  # GET /explorations/1
  # GET /explorations/1.json
  def show
  end

  # GET /explorations/new
  def new
    @exploration = Exploration.new
  end

  # GET /explorations/1/edit
  def edit
  end

  # POST /explorations
  # POST /explorations.json
  def create
    @exploration = Exploration.new(exploration_params)

    respond_to do |format|
      if @exploration.save
        format.html { redirect_to @exploration, notice: 'Exploration was successfully created.' }
        format.json { render action: 'show', status: :created, location: @exploration }
      else
        format.html { render action: 'new' }
        format.json { render json: @exploration.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /explorations/1
  # PATCH/PUT /explorations/1.json
  def update
    respond_to do |format|
      if @exploration.update(exploration_params)
        format.html { redirect_to @exploration, notice: 'Exploration was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: 'edit' }
        format.json { render json: @exploration.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /explorations/1
  # DELETE /explorations/1.json
  def destroy
    @exploration.destroy
    respond_to do |format|
      format.html { redirect_to explorations_url }
      format.json { head :no_content }
    end
  end

  def get_members
    @members = @exploration.members
  end

  def add_member
    user_name = params[:name]
    if user_name.present?
      @exploration.members.push User.find_by_name(user_name)
      @exploration.save
      render nothing: true
    else
      render nothing: true, status: 400
    end
  end

  def start
    @exploration.start!
    @exploration.save
    render nothing: true
  end

  def get_current_quest_number
    render text: @exploration.current_quest_number
  end

  def put_group_photo
    quest_number = params[:quest_number].to_i
    @exploration.put_group_photo!(quest_number)
    @exploration.save
    render nothing: true
  end

  def put_mission_photo
    quest_number = params[:quest_number].to_i
    @exploration.put_mission_photo!(quest_number)
    @exploration.save
    render nothing: true
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_exploration
      @exploration = Exploration.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def exploration_params
      params.require(:exploration).permit(:start_time, :route_id, :user_id, :current_quest_number, :current_mission_completed_number_count, :photographed, :description)
    end
end
