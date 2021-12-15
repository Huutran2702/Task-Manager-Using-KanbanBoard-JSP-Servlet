

<%--Modal add worklist--%>
<div id="myModal" class="modal">

    <!-- Modal content -->

    <div class="modal-content slideDown">

        <div class="modal-header">
            <span class="close" id="closeModal">&times;</span>
            <h2>Modal form</h2>
        </div>
        <div class="modal-body">
            <form action="" class="modal-form">
                <div class="form-row">
                    <label for="">Name</label>
                    <select class="caregiver-select" name="name" id="namepick">
                        <option value="" selected disabled>Pick an option</option>
                    </select>
                </div>
                <div class="form-row">
                    <label for="iduser">A thing</label>
                    <select class="user-select" name="user-name" id="user">
                        <option value="" selected disabled>Pick a thing</option>
                    </select>
                </div>
                <div class="form-row">
                    <label for="">Date</label>
                    <input type="datetime-local">
                </div>
                <div class="form-row">
                    <label for="">Start time</label>
                    <input type="time">
                </div>
                <div class="form-row">
                    <label for="">Event length</label>
                    <input type="text">
                </div>
        </div>
        <div class="modal-footer">
            <input type="submit" class="button good" value="Save">
        </div>
        </form>

    </div>

</div>

