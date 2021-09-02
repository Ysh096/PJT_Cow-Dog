<template>
<el-input
          class="elinput" id="tagText"
          autocomplete="off" 
          placeholder="#태그를 입력해 주세요.(최대 10개)"
          style="width: 100%;"
          v-show="visible"
          @keyup.enter="enterTag()"
          @click="focus()"
          @blur="blur()"
          />

  <el-upload action="#" list-type="picture-card" :auto-upload="false">
  <template #default>
    <i class="el-icon-plus"></i>
  </template>
  <template #file="{file}">
    <div>
      <img class="el-upload-list__item-thumbnail" :src="file.url" alt="" />
      <span class="el-upload-list__item-actions">
        <span
          class="el-upload-list__item-preview"
          @click="handlePictureCardPreview(file)"
        >
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
          v-if="!disabled"
          class="el-upload-list__item-delete"
          @click="handleDownload(file)"
        >
          <i class="el-icon-download"></i>
        </span>
        <span
          v-if="!disabled"
          class="el-upload-list__item-delete"
          @click="handleRemove(file)"
        >
          <i class="el-icon-delete"></i>
        </span>
      </span>
    </div>
  </template>
</el-upload>
<el-dialog v-model="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="" />
</el-dialog>
</template>

<script>
  export default {
    data() {
      return {
        dialogImageUrl: '',
        dialogVisible: false,
        disabled: false,
        visible: true,
      }
    },
    methods: {

    focus() {
      console.log("포커스")
      setTimeout(() => {
        this.visible = false
      }, 0)
    },

    blur() {
      console.log("블러")
      setTimeout(() => {
        this.visible = true
      }, 0)
      
    },
      handleRemove(file) {
        console.log(file)
      },
      handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url
        this.dialogVisible = true
      },
      handleDownload(file) {
        console.log(file)
      },
    },
  }
</script>